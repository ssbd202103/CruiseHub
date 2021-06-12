import axios from './URL';

export function getAttractionsByCruiseUUID(uuid: string) {
    return axios.get(`attractions/cruise/${uuid}`)
}