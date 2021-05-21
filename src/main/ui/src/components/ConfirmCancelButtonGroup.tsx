import RoundedButton from "./RoundedButton";
import React from "react";
import {useTranslation} from "react-i18next";


export interface OkCancelButtonGroupProps {
    onConfirm(): void,
    onCancel(): void
}

export function ConfirmCancelButtonGroup({onConfirm, onCancel}: OkCancelButtonGroupProps) {
    const {t} = useTranslation()

    return (
        <>
        <RoundedButton
            color="blue"
            onClick={onConfirm}
        >{t("confirm")}</RoundedButton>

        <RoundedButton
            color="pink"
            onClick={onCancel}
            style={{marginLeft: '20px'}}
        >{t("cancel")}</RoundedButton>
        </>
    )
}