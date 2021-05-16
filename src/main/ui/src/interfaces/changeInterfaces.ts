import AbstractAccount from "./AbstractAccount";

export interface ChangeEmail extends AbstractAccount {
    email: string
}

export interface ChangeAddress {
    houseNumber: number,
    street: string,
    postalCode: string,
    city: string,
    country: string
}

export interface AccountChangeData extends AbstractAccount {
    newFirstName: string,
    newSecondName: string
}

export interface ConsumerChangeData extends AccountChangeData {
    newPhoneNumber: string
}

export interface ClientChangeData extends ConsumerChangeData {
    newAddress: ChangeAddress
}

export interface BusinessWorkerChangeData extends ConsumerChangeData {

}