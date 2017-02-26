var express = require('express');
var request = require('request');
var fs = require('fs');
var app = express();

app.use(express.static('src/main/webapp'));

app.use('/lars-fastopt.js', express.static('js/target/scala-2.12/lars-fastopt.js'));
app.use('/lars-fastopt.js.map', express.static('js/target/scala-2.12/lars-fastopt.js.map'));

var server = app.listen(3000, function () {
    console.log('Dev server listening at http://%s:%s/', server.address().address, server.address().port);
});

app.post('/rest/*', function(req, res) {
  request('http://localhost:8082' + req.path).on('error', function(err) {
    console.log('err: ' + req.path + ' is unavailable');
  }).pipe(res);
});

app.get('/rest/*', function(req, res) {
  request('http://localhost:8082' + req.path).on('error', function(err) {
    fs.readFile(__dirname + '/mockdata' + req.path, function(err, data) {
      if(err)
        res.status(404).send();
      else
        res.set('Content-Type', 'application/json').send(data);
    })
  }).pipe(res);
});