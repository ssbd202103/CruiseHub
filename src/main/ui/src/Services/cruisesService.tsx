import axios from "./URL";

export function getPublishedCruises() {
    return axios.get('cruise/cruises')
}

export function getCruiseByUUID(uuid: string) {
    return axios.get(`get-cruise/${uuid}`)
}

export function getRelatedCruises(uuid: string) {
    return axios.get(`cruise/cruise_group/${uuid}`)
}