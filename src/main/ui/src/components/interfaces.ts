export interface ChangeDataComponentProps {
    open: boolean,
    onOpen(): void,
    onConfirm(): void,
    onCancel(): void
}