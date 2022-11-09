var Lab4 = (function () {

    var rates = null;

    var convert = function () {
        // INSERT YOUR CODE HERE
        var currCode = $("#target_currency").val();
        var usrAmnt = $("#usd_value")[0].valueAsNumber;
        var eurAmnt = usrAmnt / rates.rates.USD;
        console.log(currCode);
        var cnvrtAmnt = eurAmnt * rates.rates[currCode];
        
        var usr_value_string = (usrAmnt).toLocaleString('en-US', {
            style: 'currency',
            currency: 'USD',
        });

        var cnvrt_value_string = (cnvrtAmnt).toLocaleString('en-US', {
            style: 'currency',
            currency: currCode,
        });

        var p = document.createElement("p");
        $(p).append("The equivalent of " + usr_value_string + " in " + currCode + " for the date " + rates.date + " is: " + cnvrt_value_string)
        $('#output').append(p);
    };

    var getRatesAndConvert = function (rate_date, key) {

        console.log("Getting rates for " + rate_date + " ...");
        
        var ajaxURL = 'http://localhost:8180/Project1/rate?date=' + rate_date + '&key=' + key;

        // INSERT YOUR CODE HERE
        
        $.ajax({
            url: ajaxURL,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                rates = data;
                convert();
            },
            error: function(xhr, statusText, response) {
                alert("Error!");
                $("output").html(response);
                //alert(statusText.toUpperCase() + " " + xhr.status + ": You have exceeded the daily amount of requests, or the key was not found in the database!");
                
            }
        });

    };

    return {

        onClick: function () {

            var rate_date = $("#rate_date").val();
            var key = $("#access_key").val();

            if (key === "") {
                alert("You must enter a valid key!");
            }
            else {
                if (rate_date === "") {
                    alert("Please enter or select a date in the \"Date\" field!");
                }
                else {

                    // if rates have not been retrieved yet, or if the date is different, fetch new rates

                    if ((rates === null) || (rate_date !== rates["date"])) {
                        getRatesAndConvert(rate_date, key);
                    }

                    // if rates for the selected date are already available, perform the conversion

                    else {
                        convert();
                    }

                }
            }
            

        }

    };

})();