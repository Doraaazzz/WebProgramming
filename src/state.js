import { reactive } from "vue";

let target = {
    token: null,
    results: []
};

try {
    const serialized = localStorage.getItem("applicationState");
    if (serialized !== null) {
        target = JSON.parse(serialized);
    }
    target.results.forEach((result) => (result.date = new Date(result.date)));
} catch (e) {
    console.error(e);
}

export const state = reactive(target);
export const saveState = () => {
    const serialized = JSON.stringify(state);
    localStorage.setItem("applicationState", serialized);
};

state.authorized = () => state.token !== null;
