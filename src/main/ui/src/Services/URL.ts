import axios from "axios";

export default axios.create({
    baseURL: window.location.href.includes("studapp") ?
        "https://studapp.it.p.lodz.pl:8403/api" : "https://127.0.0.1:555/api/"
});