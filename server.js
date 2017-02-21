var express = require('express');
var app = express();

app.use(express.static('src/main/webapp'));

app.use('/lars-fastopt.js', express.static('js/target/scala-2.11/lars-fastopt.js'));
app.use('/lars-fastopt.js.map', express.static('js/target/scala-2.11/lars-fastopt.js.map'));

var server = app.listen(3000, function () {
    console.log('Dev server listening at http://%s:%s/', server.address().address, server.address().port);
});
