var express = require('express');
var request = require('request');
var fs = require('fs');
var app = express();

var hostname = 'http://localhost:9000'

app.use(express.static('server/public'));

app.use('/assets/client-fastopt.js', express.static('client/target/scala-2.11/client-fastopt.js'));
app.use('/assets/client-fastopt.js.map', express.static('client/target/scala-2.11/client-fastopt.js.map'));

var server = app.listen(3000, function () {
    console.log('Dev server listening at http://%s:%s/', server.address().address, server.address().port);
});

app.post('/rest/*', function(req, res) {
  request.post(hostname + req.path).on('error', function(err) {
    console.log('err: ' + req.path + ' is unavailable');
  }).pipe(res);
});

app.get('/rest/*', function(req, res) {
  request(hostname + req.path).on('error', function(err) {
    fs.readFile(__dirname + '/mockdata' + req.path, function(err, data) {
      if(err)
        res.status(404).send();
      else
        res.set('Content-Type', 'application/json').send(data);
    })
  }).pipe(res);
});