var connection = require('./sql');

function User() {
    this.get = function(log,res) {
        connection.acquire(function(err, con) {
            console.log('query');
            con.query('select * from discountList where region=?',[log], function(err, result) {
                if(result.length!=0){
                    res.send(result);
                }
                else{
                    res.send({'status':'User does not exist'});
                }
            });
        });
    };

}
module.exports = new User();