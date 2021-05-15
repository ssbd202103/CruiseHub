import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/styles'
import * as React from 'react'

export interface RoundedButtonProps {
    readonly className?: string,
    readonly color: "pink" | "yellow" | "green" | "blue" | "white"
    readonly children?: any,
    readonly style?: React.CSSProperties,
    readonly onClick?: React.MouseEventHandler<any>
}

export default function RoundedButton(props: RoundedButtonProps) {
    
    const {
        className: styles,
        children,
        color,
        style,
        onClick
    } = props

    const classes = makeStyles(theme => ({
        root: {
            padding: '8px 16px',
            fontFamily: "'Montserrat', sans-serif",
            backgroundColor: 'var(--' + color + ')',
            color: 'var(--white)',
            borderRadius: 34,
            '&:hover': {
                backgroundColor: 'var(--' + color + '-dark)'
            }
        }
    }))()

    return (
        <Button
            className={(styles || '') + ' ' + classes.root}
            style={style || undefined}
            onClick={onClick || undefined}
        >{children}</Button>
    )
}