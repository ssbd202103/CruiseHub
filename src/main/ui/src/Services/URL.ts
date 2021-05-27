import axios from "axios";

export default axios.create({
    baseURL: "https://studapp.it.p.lodz.pl:8403/api"
    // baseURL: "https://localhost:8181/api/"
});