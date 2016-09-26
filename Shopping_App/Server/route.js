var user = require('./user');

module.exports = {
    configure: function(app) {
        app.get('/info', function(req, res) {
            user.get(req.query.region, res);
            //res.send('hey');
        });
    }
};