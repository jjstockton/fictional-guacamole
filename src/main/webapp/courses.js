
function formatCourses(input,id) {
    var output = input.replace(new RegExp(
                    '([A-Z]{2,})\\s{0,1}(\\d{2,}\\w{0,1})','g'), '<a id="course" href="https://ugradcalendar.uwaterloo.ca/courses/$1/$2" target="_blank"><b>$1 $2</b></a>'
    );
    
    return output;
    
}

