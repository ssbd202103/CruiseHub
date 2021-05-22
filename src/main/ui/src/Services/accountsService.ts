import store from '../redux/store';
import axios from './URL'

export function getAllAccounts() {
    const {token} = store.getState()

    return axios.get('account/accounts', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}

export function getAccountDetailsAbout(login: string) {
    const {token} = store.getState()

    return axios.get(`account/details/${login}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}