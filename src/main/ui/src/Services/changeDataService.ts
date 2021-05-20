import store from "../redux/store";
import axios from './URL'
import {
    ChangeAddress,
    ClientChangeData,
    BusinessWorkerChangeData,
    ModeratorChangeData,
    AdministratorChangeData,
    ChangeMode
} from "../interfaces/changeInterfaces";
import getUser from "./userService";

export function changeClientData(newFirstName: string, newSecondName: string, newPhoneNumber: string) {
    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    const newAddress = store.getState().user.accessLevels.find(accessLevel => accessLevel.accessLevelType === "CLIENT")?.address || {} as ChangeAddress

    const changeAccountDataDto: ClientChangeData = {
        newFirstName,
        newSecondName,
        newPhoneNumber,
        newAddress,
        login,
        version
    }

    return axios.put('account/client/changedata', changeAccountDataDto, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}

export function changeClientAddress(newAddress: ChangeAddress) {
    const {
        user: {
            firstName: newFirstName,
            secondName: newSecondName,
            login,
            version,
            etag
        },
        token
    } = store.getState()

    const newPhoneNumber = store.getState().user.accessLevels.find(accessLevel => accessLevel.accessLevelType === "CLIENT")?.phoneNumber || "000000000"

    const changeAccountDataDto: ClientChangeData = {
        newFirstName,
        newSecondName,
        newPhoneNumber,
        newAddress,
        login,
        version
    }

    return axios.put('account/client/changedata', changeAccountDataDto, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}

export function changeBusinessWorkerData(newFirstName: string, newSecondName: string, newPhoneNumber: string) {
    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    const changeAccountDataDto: BusinessWorkerChangeData = {
        newFirstName,
        newSecondName,
        newPhoneNumber,
        login,
        version
    }

    return axios.put('account//businessworker/changedata', changeAccountDataDto, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}

export function changeModeratorData(newFirstName: string, newSecondName: string) {
    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    const changeAccountDataDto: ModeratorChangeData = {
        newFirstName,
        newSecondName,
        login,
        version
    }

    return axios.put('account/moderator/changedata', changeAccountDataDto, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}

export function changeAdministratorData(newFirstName: string, newSecondName: string) {
    const {
        user: {
            login,
            version,
            etag
        },
        token
    } = store.getState()

    const changeAccountDataDto: AdministratorChangeData = {
        newFirstName,
        newSecondName,
        login,
        version
    }

    return axios.put('account/administrator/changedata', changeAccountDataDto, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    }).then(res => {
        return getUser(token)
    })
}


export function changeDarkMode() {
    const {
        user: {
            login,
            darkMode,
            version,
            etag
        },
        token
    } = store.getState()

    const changeDto: ChangeMode = {
        login,
        version,
        newMode: !darkMode
    }

    return axios.put('account/change_mode', changeDto, {
        headers: {
            'If-Match': etag,
            'Authorization': `Bearer ${token}`
        }
    })
        .catch(error => console.log(error))
        .then(res => {
        return getUser(token)
    })
}