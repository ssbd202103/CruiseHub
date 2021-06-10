import axios from "./URL";

export function getPublishedCruises() {
    return axios.get('cruise/cruises')
}