import axios from "axios";

export default axios.create({
  //  baseURL: "http://studapp.it.p.lodz.pl:8003/api/"
    baseURL: "http://localhost:8080/api/"
});