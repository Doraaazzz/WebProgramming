<script setup>
import { ref } from "vue";
import { messages } from "../message";

let x = ref(0);
let y = ref(0);
let r = ref(2);

const xOptions = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
const rOptions = [1, 2, 3, 4];

let errors = {
    form: ref(new Array()),
    x: ref(new Array()),
    y: ref(new Array()),
    r: ref(new Array())
};

const updateErrors = (result) => {
    errors.form.value = [messages[result.message]];
    errors.x.value = [];
    errors.y.value = [];
    errors.r.value = [];
    Array.from(result.reasons).forEach((reason) => {
        errors[reason.field].value.push(messages[reason.message]);
        errors[reason.field].value.sort();
    });
};

const emit = defineEmits(["r-change", "check"]);

const handleSubmit = async () => {
    errors.form.value = [];
    errors.x.value = [];
    errors.y.value = [];
    errors.r.value = [];

    emit("check", x.value, y.value, r.value);
};

defineExpose({ updateErrors });
</script>
<template>
    <form @submit.prevent="handleSubmit">
        <div class="input-group">
            <label for="x">X: </label>
            <select id="x" v-model="x">
                <option v-for="xOption in xOptions">{{ xOption }}</option>
            </select>
        </div>
        <ul class="error">
            <li v-for="err in errors.x.value">{{ err }}</li>
        </ul>
        <div class="input-group">
            <label for="y">Y: </label>
            <input type="text" id="y" v-model="y" />
        </div>
        <ul class="error">
            <li v-for="err in errors.y.value">{{ err }}</li>
        </ul>
        <div class="input-group">
            <label for="r">R: </label>
            <select id="r" v-model="r" @change="$emit('r-change', r)">
                <option v-for="rOption in rOptions">{{ rOption }}</option>
            </select>
        </div>
        <ul class="error">
            <li v-for="err in errors.r.value">{{ err }}</li>
        </ul>

        <input type="submit" value="Проверить" class="button" />
        <ul class="error">
            <li v-for="err in errors.form.value">{{ err }}</li>
        </ul>
    </form>
</template>

<style></style>
