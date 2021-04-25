import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/styles'
import * as React from 'react'
export interface RoundedButtonProps {
    readonly className?: string,
    readonly color: "pink" | "yellow" | "green" | "blue"
    readonly children?: any,
    readonly onClick?: any,
    readonly style?: React.CSSProperties 
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
            onClick={onClick}
            className={(styles || '') + ' ' + classes.root}
            style={style || undefined}
        >{children}</Button>
    )
}