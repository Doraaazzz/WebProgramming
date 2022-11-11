"use strict";

const rSelect = document.querySelector("#select")

function rChangeHandler() {
    drawArea(rSelect.value);
}

rSelect.addEventListener("change", rChangeHandler);
drawArea(2);