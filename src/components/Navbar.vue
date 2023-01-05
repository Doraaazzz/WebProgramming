<script setup>
import { saveState, state } from "../state";

const logout = () => {
    state.token = null;
    state.results = [];
    saveState();
};
</script>

<template>
    <div className="menu">
        <nav>
            <section>
                <RouterLink to="/" class="menu-index"> Веб лабораторная №4 </RouterLink>
                <ul class="menu-details">
                    <li>Воронина Дарья</li>
                    <li>Вариант 666420</li>
                    <li>Группа P32311</li>
                </ul>
            </section>
            <section>
                <a href="#" @click="logout" v-if="state.authorized()">Выйти</a>
            </section>
        </nav>
    </div>
</template>

<style scoped>
nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.menu {
    display: fixed;
    padding: 25px;
    background-color: #555;
    color: white;
}

.menu a {
    color: inherit;
    text-decoration: none;
}

.menu-index {
    font-size: 1.5em;
    margin-right: 15px;
}

.menu-details {
    display: none;
}

@media (min-width: 842px) {
    .menu-details {
        display: inline;
        list-style: none;
        padding: 0;
    }

    .menu-details li {
        display: inline;
    }

    .menu-details li + li::before {
        content: "/";
        margin: 0 5px;
    }

    .menu-left {
        flex: 0.5;
    }

    .menu-left {
        flex: 0.5;
    }
}
</style>
