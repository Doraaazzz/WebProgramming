let WIDTH = 400;
let HEIGHT = 400;
let DEFAULT_R = 2;
let DRAW = null;
let scale = 0.014;
let attemptsArray = [];

const GRAPH_COLOR= '#386646';
const AXES_COLOR = '#fdadad';

// получаем координату x рисунке
function convertToX (x) {
    return (WIDTH / 2) + x / (2 * scale);
}

function convertToY (y) {
    return (HEIGHT / 2) - y / (2 * scale);
}

// получаем значение координаты х из координаты х с рисунка
function convertToCoordinatesX (xPoint)  {
    return (xPoint - (WIDTH / 2)) * 2 * scale;
}

function convertToCoordinatesY (yPoint) {
    return (-yPoint + (HEIGHT / 2) ) * 2 * scale;
}

function drawPoint (x, y, result, pointScale) {
    let color = (result === "false" || result === undefined) ? '#f00' : '#000000';
    DRAW.circle(pointScale * 2).fill(color).move(convertToX(x) - pointScale, convertToY(y) - pointScale);
}



function drawGraph () {
    // получили поле с указанным размером, в котором рисуем
    DRAW = SVG().addTo('#graph').size(WIDTH, HEIGHT);
    if (attemptsArray.length === 0) {
        initGraph();
    } else {
        drawWithPoints(attemptsArray);
    }
}

function initGraph ()  {
    drawArea(DEFAULT_R);
    drawAxes();
    drawAxesScale(DEFAULT_R);
    drawR(DEFAULT_R);
}

function drawWithPoints (attemptsArray) {
    drawArea(DEFAULT_R);
    drawAxes();
    drawAxesScale(DEFAULT_R);
    attemptsArray.forEach(point => drawPoint(point.x, point.y, point.result, DEFAULT_R));
    drawR(DEFAULT_R);
}



function drawAxes ()  {
    const arrowSize = 10;
    // x axe
    DRAW.line(0, (HEIGHT / 2), WIDTH, (HEIGHT / 2)).stroke({width: 1, color: AXES_COLOR});
    // points for x axe arrow
    const arrowX = (WIDTH - arrowSize) + ',' + (HEIGHT / 2 - arrowSize / 2) + ' ' + (WIDTH - arrowSize) + ',' + (HEIGHT / 2 + arrowSize / 2) + ' ' + (WIDTH) + ',' + (HEIGHT / 2);
    DRAW.polygon(arrowX).fill(AXES_COLOR);
    // x axe label
    DRAW.text('x').font({ size: 16, family: 'Courier New, Courier, monospaces', anchor: 'end', fill: AXES_COLOR}).move(WIDTH - 2 * arrowSize, HEIGHT / 2 + 2.5 * arrowSize);

    // y axe
    DRAW.line(WIDTH / 2, 0, WIDTH / 2, HEIGHT).stroke({width: 1, color: AXES_COLOR});
    // points for y axe arrow
    const arrowY = (WIDTH / 2 - arrowSize / 2) + ',' + (arrowSize) + ' ' + (WIDTH / 2 + arrowSize / 2) + ',' + (arrowSize) + ' ' + (WIDTH / 2) + ',' + (0);
    DRAW.polygon(arrowY).fill(AXES_COLOR);
    // y axe label
    DRAW.text('y').font({size: 16, family: 'Courier New, Courier, monospaces', anchor: 'end', fill: AXES_COLOR}).move(WIDTH / 2 + 1.5 * arrowSize, arrowSize / 2);
}

function drawScale (x_start, y_start,x_stop, y_stop, x_label, y_label, label) {
    DRAW.line(convertToX(x_start), convertToY(y_start), convertToX(x_stop), convertToY(y_stop)).stroke({width: 2, color: AXES_COLOR});
    DRAW.text(label).font({ size: 16, family:'Courier New, Courier, monospaces' , anchor: 'start', fill: AXES_COLOR}).move(convertToX(x_label), convertToY(y_label));
}

function drawAxesScale (r) {
    const streak = 0.1;
    drawScale(-r, -streak, -r, streak, -r - 4 * streak, 6 * streak, "-R");
    drawScale(-r / 2, -streak, -r/2 , streak,  -r / 2 - 6 * streak, 6 * streak, "-R/2");
    // drawScale(0, -streak, 0 , streak , -3 * streak, 6 * streak , "0"   );
    drawScale(r / 2, -streak,r/2,  streak, r / 2 - 4 * streak, 6 * streak, "R/2");
    drawScale(r, -streak, r, streak, r - streak , 6 * streak, "R");
    drawScale(-streak, -r, streak, -r, 2 * streak, -r + 2 * streak, "-R");
    drawScale(-streak, -r/2, streak, -r / 2, 2 * streak, -r / 2 + 2 * streak, "-R/2");
    drawScale(-streak, r/2, streak, r / 2, 2 * streak, r / 2 + 2 * streak, "R/2");
    drawScale(-streak, r, streak, r, 2 * streak, r + 2 * streak, "R");
}

function drawR (r) {
    DRAW.text('R = ' + parseFloat(r).toFixed(1)).font({ size: 16, family:'Courier New, Courier, monospaces', anchor: 'end', fill: AXES_COLOR}).move(50, 50);
}


function drawArea (r) {
    const areaPath = 'M ' + (convertToX(-r/2)) + ' ' + (convertToY(0)) + ' ' +
        'L' + (convertToX(0)) + ' ' + (convertToY(r/2)) + ' ' + 'V' + (convertToY(0)) + ' '+ 'H' + (convertToX(r/2)) + ' '+ 'V' + (convertToY(-r/2))+ ' '
        + 'H' + (convertToX(0)) + ' ' + 'A' + r/(4*scale)+ ' '+ r/(4*scale)+' 90 0 1 '+(convertToX(-r)) + ' '+ (convertToY(0)) ;
    DRAW.path(areaPath).fill(GRAPH_COLOR);
}

// fuck

clickPointEvent = (event) => {
    let coordinates = getCoords(event);
    console.log("Click working at coordinates: " + coordinates.x + ", " + coordinates.y + ", " + coordinates.r);
    if (validateR(coordinates.r)) sendGraphRequest(coordinates);
    else injectRAlert(coordinates.r);
}

//????
getCoords = (event) => {
    let coordinates = {};
    coordinates.x = convertToCoordinatesX(event.pageX - 99.5);
    coordinates.y = convertToCoordinatesY(event.pageY - 175);
    coordinates.r = $("#R_value").val();
    return coordinates;
}

function switchR (values) {
    DEFAULT_R = values.r;
    $('#graph').empty();
    drawGraph();
}

function addPoint (x, y, r, result) {
    attemptsArray.push({
        x: x,
        y: y,
        r: r,
        result: result,
    });
}

function resetPoints (newAttemptsArray) {
    if (newAttemptsArray.length !== 0) {
        attemptsArray = [];
        newAttemptsArray.forEach(point => {attemptsArray.push(JSON.parse(point));})
    }
}

function cleanGraph  ()  {
    $('#graph').empty();
    attemptsArray = [];
    drawGraph();
}

