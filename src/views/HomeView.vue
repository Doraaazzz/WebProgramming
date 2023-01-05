<script setup>
import LoginView from "./LoginView.vue";
import AreaCheckView from "./AreaCheckView.vue";

import { state } from "../state";
</script>

<template>
    <component :is="state.authorized() ? AreaCheckView : LoginView"></component>
</template>
