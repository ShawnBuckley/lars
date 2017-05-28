var express = require('express');
var request = require('request');
var fs = require('fs');
var app = express();

var apiGateway = 'http://localhost:9000';
var appRoot = 'src/main/webapp/';

app.use(express.static(appRoot));
app.use('/node_modules', express.static('node_modules'));

var server = app.listen(3000, function () {
    console.log('Dev server listening at http://%s:%s/', server.address().address, server.address().port);
});

app.post('/api/*', function(req, res) {
  request.post(apiGateway + req.path).on('error', function(err) {
    console.log('err: ' + req.path + ' is unavailable');
  }).pipe(res);
});

app.get('/api/*', function(req, res) {
  request(apiGateway + req.path).on('error', function(err) {
    fs.readFile(__dirname + '/mockdata' + req.path, function(err, data) {
      if(err)
        res.status(404).send();
      else
        res.set('Content-Type', 'application/json').send(data);
    })
  }).pipe(res);
});

// Probably angular route, server up index and let angluar deal with it
app.get('*', function(req, res) {
  fs.readFile(__dirname + '/' + appRoot + 'index.html', function(err, data) {
    if(err)
      res.status(404).send();
    else
      res.set('Content-Type', 'text/html').send(data);
  });
});