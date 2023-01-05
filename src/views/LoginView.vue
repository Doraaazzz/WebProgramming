<script setup>
import { ref } from "vue";
import { api } from "../api";
import { state, saveState } from "../state";
import { messages } from "../message";

let username = "";
let password = "";

let errors = {
    form: ref(new Array()),
    username: ref(new Array()),
    password: ref(new Array())
};

let disabled = ref(false);

const tryLogin = async () => {
    disabled.value = true;
    let response = await api("POST", "/auth/login", { username, password });
    let result = await response.json();

    if (response.ok) {
        const token = result.token;

        response = await api("GET", "/area/", null, token);
        result = await response.json();

        if (response.ok) {
            state.results = Array.from(result);
            for (let i = 0; i < state.results.length; ++i) {
                state.results[i].date = new Date(state.results[i].date * 1000);
                state.results[i].id = i + 1;
            }
        }

        state.token = token;
        saveState();
    } else {
        disabled.value = false;
        errors.form.value = [messages[result.message]];
        errors.username.value = [];
        errors.password.value = [];
        Array.from(result.reasons).forEach((reason) => {
            errors[reason.field].value.push(messages[reason.message]);
            errors[reason.field].value.sort();
        });
    }
};
</script>

<template>
    <main>
        <h1>Вход</h1>
        <form @submit.prevent="tryLogin">
            <div class="input-group">
                <label for="username">Логин</label>
                <input id="username" name="username" type="text" v-model="username" required />
            </div>
            <ul class="error">
                <li v-for="err in errors.username.value">{{ err }}</li>
            </ul>
            <div class="input-group">
                <label for="password">Пароль</label>
                <input id="password" name="password" type="password" v-model="password" required />
            </div>
            <ul class="error">
                <li v-for="err in errors.password.value">{{ err }}</li>
            </ul>
            <p>
                <RouterLink to="/register">Зарегистрироваться</RouterLink>
            </p>
            <ul class="error">
                <li v-for="err in errors.form.value">{{ err }}</li>
            </ul>
            <button type="submit" class="button" :disabled="disabled">
                {{ disabled ? "Секунду..." : "Войти" }}
            </button>
        </form>
    </main>
</template>

<style></style>
