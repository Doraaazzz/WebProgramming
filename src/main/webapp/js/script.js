const board = JXG.JSXGraph.initBoard('jxgbox', {
    axis: true,
    grid: false,
    showNavigation: false,
    showCopyright: false,
    showZoom: false
});

JXG.GeometryElement.prototype.highlight = () => {};


function drawArea(r) {

    const vertex = (x, y) => {
        const res = board.create("point", [x, y]);
        res.hideElement();
        return res;
    };

    const center = vertex(0, 0);

    const sectorPoints = [center, vertex(0, r / 2), vertex(-r / 2, 0)];
    const sector = board.create("sector", sectorPoints);

    const trianglePoints = [center, vertex(-r, 0), vertex(0, -r / 2)];
    const triangle = board.create("polygon", trianglePoints);

    const rectanglePoints = [center, vertex(0, -r), vertex(r / 2, -r), vertex(r / 2, 0)];
    const rectangle = board.create("polygon", rectanglePoints);

    const never = () => false;


//    Array.of(...triangle.borders, ...rectangle.borders).forEach(b => b.hasPoint = never);
}

drawArea(5);
