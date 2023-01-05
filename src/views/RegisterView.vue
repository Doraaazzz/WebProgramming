<script setup>
import { api } from "../api";
import { state, saveState } from "../state";
import { ref } from "vue";
import router from "../router";
import { messages } from "../message";

if (state.authorized()) {
    router.push("/");
}

let username = "";
let password = "";
let again = "";

let errors = {
    form: ref(new Array()),
    username: ref(new Array()),
    password: ref(new Array())
};

let againMessage = ref("");

const tryRegister = async () => {
    if (password === again) {
        againMessage.value = "";
        const response = await api("POST", "/auth/register", { username, password });
        const result = await response.json();

        if (response.ok) {
            state.token = result.token;
            saveState();
            router.push("/");
        } else {
            errors.form.value = [messages[result.message]];
            errors.username.value = [];
            errors.password.value = [];
            Array.from(result.reasons).forEach((reason) => {
                errors[reason.field].value.push(messages[reason.message]);
                errors[reason.field].value.sort();
            });
        }
    } else {
        againMessage.value = "Пароли не совпадают";
    }
};
</script>

<template>
    <main>
        <h1>Регистрация</h1>
        <form @submit.prevent="tryRegister">
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
            <div class="input-group">
                <label for="again">Ещё раз</label>
                <input id="again" name="again" type="password" v-model="again" required />
            </div>
            <small class="error">{{ againMessage }}</small>
            <p>
                <RouterLink to="/">Уже есть аккаунт</RouterLink>
            </p>
            <ul class="error">
                <li v-for="err in errors.form.value">{{ err }}</li>
            </ul>
            <input type="submit" value="Зарегистрироваться" class="button" />
        </form>
    </main>
</template>

<style></style>
