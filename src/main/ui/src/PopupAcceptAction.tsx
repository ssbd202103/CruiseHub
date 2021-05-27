import React from "react";
import Button from '@material-ui/core/Button';
import {useTranslation} from "react-i18next";

export interface PopupAcceptActionProps {
    trigger: boolean,
    onClick: (value: boolean) => void
}

export default function PopupAcceptAction({trigger, onClick}: PopupAcceptActionProps) {
    const {t} = useTranslation()

    return trigger ? (
            <div className="popup">
                <div className="popup-inner">
                    <Button className="close-btn" onClick={()=> {onClick(false)
                                                                    trigger=false}}>{"x"}</Button>
                    <h3>{t("accept.action")}</h3>
                    <Button className="close-btn" onClick={()=> {onClick(true)
                                                                      trigger=false}}>{(t('yes'))}</Button>
                    <Button className="close-btn" onClick={()=> {onClick(false)
                                                                       trigger=false}}>{(t('no'))}</Button>
                </div>
            </div>
    ) : null;

}