import store from "../redux/store";
import axios from './URL'

export function changeOwnPassword(oldPassword: string, newPassword: string) {

    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    return axios.put('account/change_own_password', {
        login,
        version,
        oldPassword,
        newPassword
    }, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    })
}