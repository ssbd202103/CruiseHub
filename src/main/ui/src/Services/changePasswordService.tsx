import store from "../redux/store";
import axios from './URL'
import getUser from "./userService";

export function changeOwnPassword(oldPassword: string, newPassword: string) {

    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    console.log(oldPassword, newPassword)

    return axios.put('account/change_own_password', {
        login: login,
        version: version,
        oldPassword: oldPassword,
        newPassword: newPassword
    }, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}