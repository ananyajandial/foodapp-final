function OrderModal({
    show,
    order,
    setOrder,
    selectedFood,
    onSubmit,
    onClose
}) {

    if (!show) return null;

    return (
        <div
            className="modal d-block"
            tabIndex="-1"
            style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
        >
            <div className="modal-dialog">
                <div className="modal-content">

                    <div className="modal-header">
                        <h5 className="modal-title">
                            Place Order
                        </h5>

                        <button
                            type="button"
                            className="btn-close"
                            onClick={onClose}
                        />
                    </div>

                <div className="modal-body">
                    <input className="form-control mb-2" value={selectedFood?.foodName || ""} readOnly />
                    <input className="form-control mb-2" value={selectedFood?.foodId || ""} readOnly/>
                    <input type="number" className="form-control mb-2" placeholder="User ID" value={order.userId || ""}
                    onChange={(e) =>
                        setOrder({ ...order, 
                            userId: e.target.value
                        }) 
                    }/>
                    <input type="number" className="form-control mb-2" placeholder="Quantity" value={order.quantity || ""}
                        onChange={(e) => setOrder({
                            ...order,
                            quantity: e.target.value })
                            }/>

                </div>
                <div className="modal-footer">
                    <button className="btn btn-secondary" onClick={onClose}> 
                            Cancel </button>

                    <button className="btn btn-primary" onClick={onSubmit}>
                            Place Order
                        </button>

                </div>
            </div>

        </div>
    </div>
    );
}

export default OrderModal;