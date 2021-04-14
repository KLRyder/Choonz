/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 21.673614539909124, "KoPercent": 78.32638546009088};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.0030408814465323042, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.002, 500, 1500, "Create Controller"], "isController": true}, {"data": [8.0E-4, 500, 1500, "Update Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Create User Request"], "isController": false}, {"data": [0.0079, 500, 1500, "Create Playlist HTTP Request"], "isController": false}, {"data": [6.5E-4, 500, 1500, "Read Artist By ID HTTP Request"], "isController": false}, {"data": [0.00445, 500, 1500, "Update Artist HTTP Request"], "isController": false}, {"data": [0.00665, 500, 1500, "Create Genre HTTP Request"], "isController": false}, {"data": [0.0, 500, 1500, "Delete Controller"], "isController": true}, {"data": [0.00225, 500, 1500, "Update Genre HTTP Request"], "isController": false}, {"data": [5.0E-4, 500, 1500, "Read Genre By ID HTTP Request"], "isController": false}, {"data": [0.01105, 500, 1500, "Login HTTP Request"], "isController": false}, {"data": [8.5E-4, 500, 1500, "Update Playlist HTTP Request"], "isController": false}, {"data": [0.00195, 500, 1500, "Read Playlist HTTP Request"], "isController": false}, {"data": [0.0048, 500, 1500, "Create Artists HTTP Request"], "isController": false}, {"data": [0.0089, 500, 1500, "Delete Artist HTTP Request"], "isController": false}, {"data": [0.01105, 500, 1500, "Login Controller"], "isController": true}, {"data": [8.5E-4, 500, 1500, "Read Playlist By ID HTTP Request"], "isController": false}, {"data": [0.0, 500, 1500, "Delete Track HTTP Request"], "isController": false}, {"data": [0.00115, 500, 1500, "Read Genre HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create User Controller"], "isController": true}, {"data": [9.0E-4, 500, 1500, "Read Artist HTTP Request"], "isController": false}, {"data": [0.0, 500, 1500, "Read By ID Controller"], "isController": true}, {"data": [0.0, 500, 1500, "Read Controller"], "isController": true}, {"data": [0.0, 500, 1500, "Delete Album HTTP Request"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 160001, 125323, 78.32638546009088, 29389.131036680534, 0, 399222, 40067.5, 158241.7, 291858.10000000015, 381892.76, 126.97796707958057, 4093.6170963467575, 9.793307480830416], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Create Controller", 10000, 7838, 78.38, 90146.24310000005, 12, 679286, 86082.0, 196029.6, 245944.65, 435461.32999999967, 9.372317174208883, 61.0145810849535, 3.845909301333587], "isController": true}, {"data": ["Update Controller", 10000, 9699, 96.99, 99529.33009999993, 3, 681048, 12180.0, 340698.5, 470884.8999999973, 617592.0099999999, 7.978394507673221, 47.50715686919821, 2.065930460313471], "isController": true}, {"data": ["Create User Request", 1, 0, 0.0, 25.0, 25, 25, 25.0, 25.0, 25.0, 25.0, 40.0, 16.640625, 10.703125], "isController": false}, {"data": ["Create Playlist HTTP Request", 10000, 5428, 54.28, 30051.876799999904, 1, 385920, 4067.0, 90791.5, 102943.4, 171300.79, 12.724378509542648, 24.798297724690254, 2.475681923302536], "isController": false}, {"data": ["Read Artist By ID HTTP Request", 10000, 7591, 75.91, 35850.98730000005, 0, 389484, 4060.0, 133355.39999999994, 201211.34999999878, 365122.94, 7.970223245953119, 243.93197482953684, 0.4201770933791355], "isController": false}, {"data": ["Update Artist HTTP Request", 10000, 9274, 92.74, 33233.09360000003, 0, 388487, 4060.0, 115029.99999999997, 162951.55, 353978.04999999993, 8.016629696642715, 15.42947878579325, 0.7353183130586088], "isController": false}, {"data": ["Create Genre HTTP Request", 10000, 6615, 66.15, 28856.1462, 0, 385160, 4064.0, 91454.9, 111438.49999999993, 225673.68999999994, 10.594915076458204, 23.272743897262696, 1.2927317343001898], "isController": false}, {"data": ["Delete Controller", 10000, 10000, 100.0, 32408.841200000046, 0, 388827, 4060.0, 111274.9, 151978.59999999992, 347036.8899999999, 8.043195175369807, 33.82286732669126, 0.652203374251078], "isController": true}, {"data": ["Update Genre HTTP Request", 10000, 9179, 91.79, 33170.73460000008, 0, 388580, 4060.0, 114580.99999999997, 164159.1999999998, 358780.35, 7.993924617290858, 15.894027570296574, 0.6685387855230025], "isController": false}, {"data": ["Read Genre By ID HTTP Request", 10000, 7704, 77.04, 37356.48950000001, 0, 390995, 4060.0, 133873.0, 235375.94999999946, 375904.93999999994, 7.971265183267358, 520.5901142561295, 0.4012403276170062], "isController": false}, {"data": ["Login HTTP Request", 10000, 3251, 32.51, 35062.702100000104, 34, 147962, 24239.5, 92083.29999999999, 102722.65, 108382.97, 22.32970252370298, 26.650818334751268, 3.870598798187498], "isController": false}, {"data": ["Update Playlist HTTP Request", 10000, 8947, 89.47, 33125.5019, 0, 388106, 4060.0, 115324.59999999999, 174079.89999999994, 358359.88999999984, 7.978419969666048, 16.288171098562607, 0.6668813755055326], "isController": false}, {"data": ["Read Playlist HTTP Request", 10000, 7387, 73.87, 34383.844099999944, 1, 390253, 4061.0, 108473.89999999998, 225481.69999999925, 356939.51, 8.665909266196802, 1622.096411781423, 0.5109120520158206], "isController": false}, {"data": ["Create Artists HTTP Request", 10000, 7396, 73.96, 31238.22010000011, 0, 385575, 4062.0, 99861.09999999999, 175218.74999999994, 317001.45999999996, 9.372870600960344, 22.16316945581816, 0.8789061584680604], "isController": false}, {"data": ["Delete Artist HTTP Request", 10000, 9212, 92.12, 32408.840999999982, 0, 388827, 4060.0, 111274.9, 151978.59999999992, 347036.8899999999, 8.043143421311918, 15.018660092737443, 0.6521991776388548], "isController": false}, {"data": ["Login Controller", 10000, 3251, 32.51, 35062.702100000104, 34, 147962, 24239.5, 92083.29999999999, 102722.65, 108382.97, 22.329552940020587, 26.6506398044936, 3.870572869565266], "isController": true}, {"data": ["Read Playlist By ID HTTP Request", 10000, 7833, 78.33, 35475.59549999999, 0, 390759, 4060.0, 132252.2, 230187.39999999973, 370441.67999999993, 7.980387399925942, 706.5157232086425, 0.38904856175463187], "isController": false}, {"data": ["Delete Track HTTP Request", 10000, 10000, 100.0, 0.0, 0, 0, 0.0, 0.0, 0.0, 0.0, 8.043214583313226, 9.402077984595635, 0.0], "isController": false}, {"data": ["Read Genre HTTP Request", 10000, 7693, 76.93, 34541.02169999999, 1, 390922, 4060.0, 123501.3, 226986.84999999934, 366191.97999999986, 8.259472996066012, 754.0343960461696, 0.4228616263129465], "isController": false}, {"data": ["Create User Controller", 1, 0, 0.0, 25.0, 25, 25, 25.0, 25.0, 25.0, 25.0, 40.0, 16.640625, 10.703125], "isController": true}, {"data": ["Read Artist HTTP Request", 10000, 7813, 78.13, 35473.978399999905, 0, 399222, 4060.0, 132200.8, 240195.6999999998, 370305.1399999999, 8.047480132783422, 281.9906039382356, 0.3888905479831003], "isController": false}, {"data": ["Read By ID Controller", 10000, 8681, 86.81, 108683.07229999991, 3, 693059, 12182.0, 358732.79999999993, 488220.94999999925, 667687.2799999999, 7.961872186473417, 1468.5296038704848, 1.2086502967091193], "isController": true}, {"data": ["Read Controller", 10000, 8914, 89.14, 104398.84420000014, 4, 684627, 12190.0, 323406.1, 393724.0999999999, 650890.2799999999, 8.038843692723239, 2520.301843036804, 1.2739816606341843], "isController": true}, {"data": ["Delete Album HTTP Request", 10000, 10000, 100.0, 1.9999999999999968E-4, 0, 1, 0.0, 0.0, 0.0, 0.0, 8.043201644673873, 9.402062860033814, 0.0], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["400", 1379, 1.1003566783431613, 0.8618696133149167], "isController": false}, {"data": ["500", 18, 0.01436288630179616, 0.01124992968793945], "isController": false}, {"data": ["Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/albums/delete/${albumid}", 10000, 7.9793812787756435, 6.249960937744139], "isController": false}, {"data": ["404", 8512, 6.792049344493828, 5.319966750207811], "isController": false}, {"data": ["Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 1367, 1.0907814208086306, 0.8543696601896238], "isController": false}, {"data": ["Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/tracks/delete/${trackid}", 10000, 7.9793812787756435, 6.249960937744139], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 94047, 75.0436871125013, 58.779007631202305], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 160001, 125323, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 94047, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/albums/delete/${albumid}", 10000, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/tracks/delete/${trackid}", 10000, "404", 8512, "400", 1379], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Create Playlist HTTP Request", 10000, 5428, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 4985, "400", 428, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 15, null, null, null, null], "isController": false}, {"data": ["Read Artist By ID HTTP Request", 10000, 7591, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7399, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 192, null, null, null, null, null, null], "isController": false}, {"data": ["Update Artist HTTP Request", 10000, 9274, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 6735, "404", 2407, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 132, null, null, null, null], "isController": false}, {"data": ["Create Genre HTTP Request", 10000, 6615, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 6126, "400", 464, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 25, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["Update Genre HTTP Request", 10000, 9179, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7019, "404", 2006, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 153, "500", 1, null, null], "isController": false}, {"data": ["Read Genre By ID HTTP Request", 10000, 7704, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7553, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 150, "500", 1, null, null, null, null], "isController": false}, {"data": ["Login HTTP Request", 10000, 3251, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 3251, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Update Playlist HTTP Request", 10000, 8947, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7258, "404", 1509, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 179, "500", 1, null, null], "isController": false}, {"data": ["Read Playlist HTTP Request", 10000, 7387, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7323, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 62, "500", 2, null, null, null, null], "isController": false}, {"data": ["Create Artists HTTP Request", 10000, 7396, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 6871, "400", 487, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 38, null, null, null, null], "isController": false}, {"data": ["Delete Artist HTTP Request", 10000, 9212, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 6569, "404", 2590, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 53, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["Read Playlist By ID HTTP Request", 10000, 7833, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7686, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 143, "500", 4, null, null, null, null], "isController": false}, {"data": ["Delete Track HTTP Request", 10000, 10000, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/tracks/delete/${trackid}", 10000, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Read Genre HTTP Request", 10000, 7693, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7589, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 98, "500", 6, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["Read Artist HTTP Request", 10000, 7813, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to localhost:8082 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect", 7683, "Non HTTP response code: java.net.BindException/Non HTTP response message: Address already in use: connect", 127, "500", 3, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Delete Album HTTP Request", 10000, 10000, "Non HTTP response code: java.net.URISyntaxException/Non HTTP response message: Illegal character in path at index 37: http://localhost:8082/albums/delete/${albumid}", 10000, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
