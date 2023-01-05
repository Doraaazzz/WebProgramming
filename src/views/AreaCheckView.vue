<script setup>
import { ref } from "vue";
import Graph from "../components/Graph.vue";
import PointForm from "../components/PointForm.vue";
import ResultTable from "../components/ResultTable.vue";
import { state, saveState } from "../state";
import { api } from "../api";

const graph = ref(null);
const form = ref(null);

const handleRChange = (newR) => graph.value.changeR(newR);
const handleCheck = async (x, y, r) => {
    const response = await api("POST", "/area/check", { x, y, r }, state.token);
    const result = await response.json();

    if (response.ok) {
        result.id = state.results.length + 1;
        result.date = new Date(result.date * 1000);
        state.results.push(result);
        saveState();
        graph.value.addPoint(result);
    } else {
        form.value.updateErrors(result);
    }
};
</script>

<template>
    <main>
        <section>
            <h1>Координаты</h1>
            <PointForm ref="form" @r-change="handleRChange" @check="handleCheck" />
        </section>
        <section>
            <h1>График</h1>
            <Graph ref="graph" @check="handleCheck" />
        </section>
        <section>
            <h1>Результаты</h1>
            <ResultTable />
        </section>
    </main>
</template>

<style scoped>
main {
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: space-around;
    width: 100%;
    margin: 0;
}
section {
    max-width: 100%;
}
</style>
