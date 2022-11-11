"use strict";


function drawArea(r) {
    const board = JXG.JSXGraph.initBoard('jxgbox', {
        axis: true,
        grid: false,
        showNavigation: false,
        showCopyright: false,
        showZoom: false,
        defaultAxes: {
            x: { ticks: { visible: true, majorHeight: 5 } },
            y: { ticks: { visible: true, majorHeight: 5 } }
        }
    });

    JXG.GeometryElement.prototype.highlight = () => {};

    const vertex = (x, y) => {
        const res = board.create("point", [x, y]);
        res.hideElement();
        return res;
    };

    const center = vertex(0, 0);

    const options = {
        fillColor: "#fa3232",
        fillOpacity: .3,
        strokeWidth: 0
    };

    const sectorPoints = [center, vertex(0, r / 2), vertex(-r / 2, 0)];
    const sector = board.create("sector", sectorPoints, options);

    const trianglePoints = [center, vertex(-r, 0), vertex(0, -r / 2)];
    const triangle = board.create("polygon", trianglePoints, options);

    const rectanglePoints = [center, vertex(0, -r), vertex(r / 2, -r), vertex(r / 2, 0)];
    const rectangle = board.create("polygon", rectanglePoints, options);

    Array.of(...triangle.borders, ...rectangle.borders).forEach(b => b.hideElement());
}
