import axios from "axios";

export default axios.create({
    baseURL: window.location.href.includes("studapp") ?
        "https://studapp.it.p.lodz.pl:8403/api" : "http://ssbd03-app-temp-tua02.apps.okd.cti.p.lodz.pl/api/"
});