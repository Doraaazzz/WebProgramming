"use strict";

export class Vertex {
    x;
    y;
    point;

    constructor(x, y, r, board) {
        this.x = x;
        this.y = y;
        this.point = board.create("point", [x * r, y * r]);
        this.point.hideElement();
    }

    rescale(r) {
        this.point.moveTo([this.x * r, this.y * r]);
    }
}

export class Area {
    static AREA_COLOR = "#0384ff";
    static POINT_HIT_COLOR = "#0f0";
    static POINT_MISS_COLOR = "#f00";

    constructor(r, emit) {
        this.r = r;
        this.emit = emit;

        this.initBoard();
        this.initVerticesAndDraw(r);
        this.points = [];
        this.rescale(r);

        this.handleClick = this.handleClick.bind(this);

        this.board.on("down", this.handleClick);
    }

    async handleClick(e) {
        console.log("click");
        let i;
        if (e[JXG.touchProperty]) {
            i = 0; // finger number
        }

        const [x, y] = this.getClickCoordinates(e, i);
        this.emit("check", x, y, this.r);
    }

    initBoard() {
        this.board = JXG.JSXGraph.initBoard("jxgbox", {
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
    }

    initVerticesAndDraw(r) {
        const center = new Vertex(0, 0, r, this.board);
        const sectorVertices = [center, new Vertex(0, 0.5, r, this.board), new Vertex(-0.5, 0, r, this.board)];
        const triangleVertices = [center, new Vertex(1, 0, r, this.board), new Vertex(0, -0.5, r, this.board)];
        const rectangleVertices = [
            center,
            new Vertex(0, 0.5, r, this.board),
            new Vertex(1, 0.5, r, this.board),
            new Vertex(1, 0, r, this.board)
        ];

        const options = {
            fillColor: Area.AREA_COLOR,
            fillOpacity: 0.5,
            strokeWidth: 0
        };

        const jsxPoints = (vertices) => Array.from(vertices).map((each) => each.point);

        const polygon = (vertices) => this.board.create("polygon", jsxPoints(vertices), options);
        this.board.create("sector", jsxPoints(sectorVertices), options);

        const borders = [...polygon(triangleVertices).borders, ...polygon(rectangleVertices).borders];

        Array.from(borders).forEach((b) => b.hideElement());

        this.vertices = [...sectorVertices, ...triangleVertices, ...rectangleVertices];
    }

    rescale(r) {
        this.r = r;
        this.vertices.forEach((v) => v.rescale(r));
    }

    put(x, y, r, hit) {
        const options = {
            fillColor: hit ? Area.POINT_HIT_COLOR : Area.POINT_MISS_COLOR,
            strokeColor: "#000",
            strokeWidth: 1,
            fixed: true,
            showInfobox: false
        };

        const hitStatus = hit ? "Попадание" : "Промах";
        const point = this.board.create("point", [x, y], options);

        point.on("over", () => {
            point.prevR = this.r;
            this.rescale(r);
            point.label.showElement();
        });
        point.on("out", () => {
            this.rescale(point.prevR);
            point.label.hideElement();
        });

        point.label.hideElement();
        point.label.setText(`${hitStatus} (${x.toFixed(2)}, ${y.toFixed(2)}, ${r.toFixed(2)})`);

        this.points.push(point);
    }
    getClickCoordinates(e, i) {
        const cPos = this.board.getCoordsTopLeftCorner(e, i);
        const absPos = JXG.getPosition(e, i);
        const dx = absPos[0] - cPos[0];
        const dy = absPos[1] - cPos[1];

        const calculatedCoordinates = new JXG.Coords(JXG.COORDS_BY_SCREEN, [dx, dy], this.board).usrCoords;
        return calculatedCoordinates.slice(1);
    }

    clearPoints() {
        this.points.forEach((p) => this.board.removeObject(p));
    }
}
