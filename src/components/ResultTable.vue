<script setup>
import { state } from "../state";
</script>

<template>
    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Попадание</th>
                <th>Дата</th>
                <th>Выполнение, мс</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="result in state.results">
                <td>{{ result.id }}</td>
                <td>{{ result.x.toFixed(2) }}</td>
                <td>{{ result.y.toFixed(2) }}</td>
                <td>{{ result.r.toFixed(2) }}</td>
                <td>{{ result.hit ? "Да" : "Нет" }}</td>
                <td>{{ result.date.toLocaleDateString() }} {{ result.date.toLocaleTimeString() }}</td>
                <td>{{ result.executionTime }}</td>
            </tr>
        </tbody>
    </table>
</template>

<style scoped>
table,
th,
td {
    border: 1px solid black;
}

th,
td {
    padding: 5px;
}

table {
    border-collapse: collapse;
    overflow-x: scroll;
    display: block;
    max-width: fit-content;
}
</style>
