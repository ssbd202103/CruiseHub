import { ChangeEmail } from '../interfaces/changeInterfaces'
import store from "../redux/store";
import axios from './URL'

export function changeEmail(newEmail: string) {

    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    return axios.put('account/change_email', {
            newEmail,
            login,
            version
        }, {
            headers: {
                "If-Match": etag,
                "Authorization": `Bearer ${token}`
            }
        })
}