"use strict";

const xInput = document.querySelector("#x_value");
const yCheckboxes = Array.from(document.querySelectorAll("input[name=coordinate_y]"))
const rSelect = document.querySelector("#select");
const xContainer = document.querySelector("#x-container");
const yContainer = document.querySelector("#y-container");
const rContainer = document.querySelector("#r-container");
const form = document.querySelector("form");
const resetButton = document.querySelector("button[type=reset]")

function rChangeHandler() {
    window.area.rescale(parseFloat(rSelect.value));
}

function makeBad(container, message) {
    container.classList.add("bad");
    container.dataset.message = message;
    document.querySelector(".ducks").classList.add("mad");
}

function dismissError(container) {
    return () => {
        container.classList.remove("bad");
        if (document.querySelector(".bad") == null) {
            document.querySelector(".ducks").classList.remove("mad");
        }
    }
}

function validate(e) {
    const yCheckedList = Array.from(document.querySelectorAll("input[name=coordinate_y]:checked"));
    
    let x, ys, r;
    
    if (isNaN(xInput.value) || isNaN(parseFloat(xInput.value))) {
        makeBad(xContainer, "<~ Must contain a number");
        e.preventDefault();
    } else {
        x = parseFloat(xInput.value);
        if (x < -5 || x > 5) {
            makeBad(xContainer, "<~ Must be in [-5; 5]");
            e.preventDefault();
        }
    }

    if (yCheckedList.length == 0) {
        makeBad(yContainer, "<~ Check some of these");
        e.preventDefault();
    } else {
        ys = yCheckedList.map(checkbox => parseFloat(checkbox.value));
    }

    if (isNaN(rSelect.value) || isNaN(parseFloat(rSelect.value))) {
        makeBad(rContainer, "<~ Select something");
        e.preventDefault();
    }
}

async function checkArea(coordinate_x, coordinate_y, coordinate_r) {
    const params = new URLSearchParams({coordinate_x, coordinate_y, coordinate_r, asJson: true});
    const request = await fetch(`checkArea?${params}`);

    if (request.ok) {
        return request.json();
    } else {
        alert(":(");
        return null;
    }
}

async function reset() {
    xInput.value = "";
    yCheckboxes.forEach(y => y.checked = false);
    rSelect.value = "nothing";
    dismissError(xContainer)();
    dismissError(yContainer)();
    dismissError(rContainer)();

    const request = await fetch("ControllerServlet?clear=true");

    if (request.ok) {
        area.clearPoints();
        const oldBody = document.querySelector("#result_table tbody");
        const newBody = document.createElement("tbody");
        oldBody.parentNode.replaceChild(newBody, oldBody);
    }
}

resetButton.addEventListener("click", reset);

rSelect.addEventListener("change", rChangeHandler);

form.addEventListener("submit", validate);

xInput.addEventListener("input", dismissError(xContainer));
yCheckboxes.forEach(y => y.addEventListener("click", dismissError(yContainer)));
rSelect.addEventListener("change", dismissError(rContainer));

area.board.on("down", async (e) => {
    if (isNaN(rSelect.value) || isNaN(parseFloat(rSelect.value))) {
        makeBad(rContainer, "<~ Select something");
        return;
    }

    const r = parseFloat(rSelect.value);

    let i;
    if (e[JXG.touchProperty]) {
        i = 0; // finger number
    }

    const [x, y] = area.getClickCoordinates(e, i);

    const result = await checkArea(x, y, r);
    if (result !== null) {
        const {x, y, r, hit, date, executionTime} = result;
        const rowValues = [x, y, r, hit, date, executionTime];

        const table = document.querySelector("#result_table");
        const newRow = table.insertRow(-1);
        rowValues.forEach(v => {
            const cell = newRow.insertCell(-1);
            const text = document.createTextNode(v);
            cell.appendChild(text);
        });

        area.put(x, y, r, hit);
    }
});