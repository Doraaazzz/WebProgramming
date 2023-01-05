<script setup>
import { ref, onMounted } from "vue";
import { Area } from "../graph";
import { state } from "../state";

let r = ref(2.0);
let area = null;

const changeR = (newR) => {
    r.value = newR;
    area.rescale(r.value);
};

const addPoint = (result) => {
    const { x, y, r, hit } = result;

    area.put(x, y, r, hit);
};

defineExpose({ changeR, addPoint });

const emit = defineEmits(["check"]);

onMounted(() => {
    area = new Area(r.value, emit);

    Array.from(state.results).forEach(addPoint);
});
</script>

<template>
    <div id="jxgbox" class="jxgbox"></div>
</template>

<style scoped>
.jxgbox {
    width: 350px;
    height: 350px;
}
</style>
