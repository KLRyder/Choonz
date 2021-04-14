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

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [1.0, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Update Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Create User Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Playlist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Update Artist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Genre HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Genre By ID HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Album By ID HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Playlist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Artists HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Delete Artist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Login Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Delete Track HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Delete Genre HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Album HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Track HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Genre HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Album HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Update Album HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Update Track HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Delete Playlist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create User Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Read Artist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Delete Album HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Create Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Read Artist By ID HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Track HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Delete Controller"], "isController": true}, {"data": [1.0, 500, 1500, "Read Track By ID HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Update Genre HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Login HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Update Playlist HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read Playlist By ID HTTP Request"], "isController": false}, {"data": [1.0, 500, 1500, "Read By ID Controller"], "isController": true}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 27, 0, 0.0, 9.444444444444446, 4, 73, 6.0, 18.59999999999999, 55.399999999999906, 73.0, 1.0158013544018059, 0.6107005972535742, 0.2897355859668924], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Update Controller", 1, 0, 0.0, 39.0, 39, 39, 39.0, 39.0, 39.0, 39.0, 25.64102564102564, 89.81870993589743, 44.69651442307692], "isController": true}, {"data": ["Create User Request", 1, 0, 0.0, 29.0, 29, 29, 29.0, 29.0, 29.0, 29.0, 34.48275862068965, 14.345366379310343, 9.226831896551724], "isController": false}, {"data": ["Create Playlist HTTP Request", 1, 0, 0.0, 16.0, 16, 16, 16.0, 16.0, 16.0, 16.0, 62.5, 34.3017578125, 24.59716796875], "isController": false}, {"data": ["Update Artist HTTP Request", 1, 0, 0.0, 7.0, 7, 7, 7.0, 7.0, 7.0, 7.0, 142.85714285714286, 96.12165178571428, 44.64285714285714], "isController": false}, {"data": ["Create Genre HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 83.17057291666667, 53.873697916666664], "isController": false}, {"data": ["Read Genre By ID HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 141.796875, 47.0703125], "isController": false}, {"data": ["Read Album By ID HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 246.58203125, 58.837890625], "isController": false}, {"data": ["Read Playlist HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 137.6953125, 58.837890625], "isController": false}, {"data": ["Create Artists HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 92.578125, 62.3046875], "isController": false}, {"data": ["Delete Artist HTTP Request", 1, 0, 0.0, 7.0, 7, 7, 7.0, 7.0, 7.0, 7.0, 142.85714285714286, 48.828125, 37.109375], "isController": false}, {"data": ["Login Controller", 1, 0, 0.0, 73.0, 73, 73, 73.0, 73.0, 73.0, 73.0, 13.698630136986301, 7.277397260273973, 3.5183005136986303], "isController": true}, {"data": ["Delete Track HTTP Request", 1, 0, 0.0, 7.0, 7, 7, 7.0, 7.0, 7.0, 7.0, 142.85714285714286, 48.828125, 36.96986607142857], "isController": false}, {"data": ["Delete Genre HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 56.96614583333333, 43.131510416666664], "isController": false}, {"data": ["Create Album HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 135.25390625, 92.041015625], "isController": false}, {"data": ["Create Track HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 132.8125, 78.515625], "isController": false}, {"data": ["Read Genre HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 142.1875, 46.484375], "isController": false}, {"data": ["Read Album HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 247.0703125, 58.10546875], "isController": false}, {"data": ["Update Album HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 158.52864583333334, 62.5], "isController": false}, {"data": ["Update Track HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 113.28125, 65.91796875], "isController": false}, {"data": ["Delete Playlist HTTP Request", 1, 0, 0.0, 7.0, 7, 7, 7.0, 7.0, 7.0, 7.0, 142.85714285714286, 48.828125, 37.388392857142854], "isController": false}, {"data": ["Create User Controller", 1, 0, 0.0, 29.0, 29, 29, 29.0, 29.0, 29.0, 29.0, 34.48275862068965, 14.345366379310343, 9.226831896551724], "isController": true}, {"data": ["Read Artist HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 134.9609375, 46.6796875], "isController": false}, {"data": ["Read Controller", 1, 0, 0.0, 22.0, 22, 22, 22.0, 22.0, 22.0, 22.0, 45.45454545454545, 167.43607954545456, 53.000710227272734], "isController": true}, {"data": ["Delete Album HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 56.96614583333333, 43.131510416666664], "isController": false}, {"data": ["Create Controller", 1, 0, 0.0, 36.0, 36, 36, 36.0, 36.0, 36.0, 36.0, 27.777777777777775, 75.439453125, 49.69618055555556], "isController": true}, {"data": ["Read Artist By ID HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 134.5703125, 47.265625], "isController": false}, {"data": ["Read Track HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 189.697265625, 58.10546875], "isController": false}, {"data": ["Delete Controller", 1, 0, 0.0, 33.0, 33, 33, 33.0, 33.0, 33.0, 33.0, 30.303030303030305, 51.7874053030303, 39.32883522727273], "isController": true}, {"data": ["Read Track By ID HTTP Request", 1, 0, 0.0, 4.0, 4, 4, 4.0, 4.0, 4.0, 4.0, 250.0, 189.208984375, 58.837890625], "isController": false}, {"data": ["Update Genre HTTP Request", 1, 0, 0.0, 14.0, 14, 14, 14.0, 14.0, 14.0, 14.0, 71.42857142857143, 49.94419642857143, 22.530691964285715], "isController": false}, {"data": ["Login HTTP Request", 1, 0, 0.0, 73.0, 73, 73, 73.0, 73.0, 73.0, 73.0, 13.698630136986301, 7.277397260273973, 3.5183005136986303], "isController": false}, {"data": ["Update Playlist HTTP Request", 1, 0, 0.0, 6.0, 6, 6, 6.0, 6.0, 6.0, 6.0, 166.66666666666666, 83.33333333333333, 57.45442708333333], "isController": false}, {"data": ["Read Playlist By ID HTTP Request", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 109.765625, 47.65625], "isController": false}, {"data": ["Read By ID Controller", 1, 0, 0.0, 23.0, 23, 23, 23.0, 23.0, 23.0, 23.0, 43.47826086956522, 159.73165760869566, 51.333220108695656], "isController": true}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 27, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
