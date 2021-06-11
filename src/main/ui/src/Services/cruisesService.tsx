import axios from "./URL";

export function getPublishedCruises() {
    return axios.get('cruise/cruises')
}

export function getCruiseByUUID(uuid: string) {
    return axios.get(`cruise/${uuid}`)
}