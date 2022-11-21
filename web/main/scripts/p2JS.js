var Project2 = (function () {
    
    var test = function(data) {
        var sections = data.sections;
        var table = document.createElement("table");
        var dash = " - ";
        var section = sections[i];
        var tr1 = document.createElement("tr");
        var tr2 = document.createElement("tr");
        var th = document.createElement("th");
        var td = document.createElement("td");
        var br = document.createElement("br");
        var tbody = document.createElement("tbody");
        var span = document.createElement("span");
        
        span.id = "fieldlabeltext";
            span.name = "fieldlabeltext";
    };
    
    var createSearchResults = function (data) {
        var sections = data.sections;
        var table = document.createElement("table");
        var dash = " - ";
        var tbody = document.createElement("tbody");
        
        
        
        for (var i = 0; i < sections.length; ++i) {
            var section = sections[i];
            var tr1 = document.createElement("tr");
            var tr2 = document.createElement("tr");
            
            var th = document.createElement("th");
            th.class = "ddtitle";
            th.id = "sectionheader";
            th.name = "sectionheader";
            th.innerHTML = section.description + dash + section.crn + dash + 
                    section.subjectid + " " + section.num + dash + section.section;
            tr1.append(th);
            
            var outerTD = document.createElement("td");
            outerTD.class = "dddefault";
                 
            var spanInners = ["Associated Term: ", "Level: ", section.credits + " Credits"];
            var spanOuters = [section.termname, section.level, ""];
            
            for (var j = 0; j < spanInners.length; j++) {
                var span = document.createElement("span");
                var br = document.createElement("br");
                span.id = "fieldlabeltext";
                span.name = "fieldlabeltext";
                span.innerHTML = spanInners[j];
                outerTD.append(span);
                outerTD.append(spanOuters[j]);
                outerTD.append(br);
                if (j === spanInners.length - 1) {
                    var br = document.createElement("br");
                    outerTD.append(br);
                }
            }
            
            var sectTbody = document.createElement("tbody");
            var sectHeaderTR = document.createElement("tr");
            var sectTableTR = document.createElement("tr");
            var sectionHeaders = ["Time", "Days", "Where", "Date Range", "Schedule Type", "Instructor(s)"];
            var sectionData = [section.scheduletypeid, section.days, section.where,
                section.termstart + dash + section.termend, section.scheduletype, section.instructor];
            
            for (var j = 0; j < sectionHeaders.length; j++) {
                var th = document.createElement("th");
                th.class = "ddheader";
                th.scope = "col";
                th.innerHTML = sectionHeaders[j];
                sectHeaderTR.append(th);
            }
            
           
            for (var j = 0; j < sectionData.length; j++) {
                var td = document.createElement("td");
                td.class = "dddefault";
                
                if (j == 0) {
                    if (sectionData[j] === "ONL") {
                        td.innerHTML = "TBA";
                    }
                    else {
                        var inner = section.start + dash + section.end;
                        td.innerHTML = inner;
                    }
                }
                else {
                    td.innerHTML = sectionData[j];
                }    
                
                sectTableTR.append(td);
            }
            sectTbody.append(sectHeaderTR);
            sectTbody.append(sectTableTR);
            sectTbody.append(br);
            
            outerTD.append(sectTbody);
            
            
            tr2.append(outerTD);
            
            tbody.append(tr1);
            tbody.append(tr2);
            
            var table = document.createElement("table");
            table.id = "datadisplaytable";
            table.name = "datadisplaytable";
            table.append(tbody);
        }
        
        $("#output").append(table);
    };


    return {

        getSearchResults: function () {
            $.ajax({
                url: "http://localhost:8180/Project2/main/search",
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        $("#output").html("No classes were found that meet your search criteria ");
                    }
                    else {
                        createSearchResults(data);
                    }
                    
                }
            });
          
        },
        register: function() {
            $.ajax({
                url: "http://localhost:8180/Project2/main/register",
                method: 'POST',
                data: $('#regform').serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        $("#output").html("No classes were found that meet your search criteria ");
                    }
                    else {
                        createSearchResults(data);
                    }
                    
                }
            });
            
            return false;
        },
        
        getSchedule: function() {
            $.ajax({
                url: "http://localhost:8180/Project2/main/register",
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        console.log("y");
                        $("#output").html("No classes have been registered! Register for classes here:"
                                + "<br><br><a href=\"term_1.jsp\">"
                                + "Register for Classes</a>");    
                    }
                    else {
                        createSearchResults(data);
                    }
                    
                },
                error: function() {
                    $("#output").html("No classes have been registered! Register for classes here:"
                                + "<br><br><a href=\"term_1.jsp\">"
                                + "Register for Classes</a>");  
                }
            });
            
            return false;
        },
        
        delete: function() {
            console.log($('#delform').serialize());
            $.ajax({
                url: "http://localhost:8180/Project2/main/register?" + $("#delform").serialize(),
                method: 'DELETE',
                dataType: 'json',
                success: function (data) {
                    $("#output").html("Course dropped successfully!");
                },
                error: function() {
                    $("#output").prepend("<p>The CRN is either not valid, or does not belong to a class" +
                                " you've registered for! Please try again.</p>");
                }
            });
            
            return false;
        }

    };

})();