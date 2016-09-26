var express = require('express');
var bodyparser = require('body-parser');
var app = express();

app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

var connection = require('./sql');
var routes = require('./route');

connection.init();
routes.configure(app);

var PORT = process.env.PORT || 3000;

app.use(express.static(__dirname + '/assests'));

var server = app.listen(PORT, function() {
    console.log('Server listening on port ' + PORT);
});