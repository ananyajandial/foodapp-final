function FoodEditModal({
    show,
    food,
    setFood,
    onSave,
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
                            Edit Food
                        </h5>

                        <button
                            type="button"
                            className="btn-close"
                            onClick={onClose}
                        />
                    </div>

                    <div className="modal-body">

                        <input
                            className="form-control mb-2"
                            placeholder="Food Name"
                            value={food.foodName || ""}
                            onChange={(e) =>
                                setFood({
                                    ...food,
                                    foodName: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-2"
                            placeholder="Category"
                            value={food.category || ""}
                            onChange={(e) =>
                                setFood({
                                    ...food,
                                    category: e.target.value
                                })
                            }
                        />

                        <input
                            type="number"
                            className="form-control mb-2"
                            placeholder="Price"
                            value={food.price || ""}
                            onChange={(e) =>
                                setFood({
                                    ...food,
                                    price: e.target.value
                                })
                            }
                        />

                        <input
                            className="form-control mb-2"
                            placeholder="Description"
                            value={food.description || ""}
                            onChange={(e) =>
                                setFood({
                                    ...food,
                                    description: e.target.value
                                })
                            }
                        />

                        <div className="form-check">

                            <input
                                type="checkbox"
                                className="form-check-input"
                                checked={food.available || false}
                                onChange={(e) =>
                                    setFood({
                                        ...food,
                                        available: e.target.checked
                                    })
                                }
                            />

                            <label className="form-check-label">
                                Available
                            </label>

                        </div>

                    </div>

                    <div className="modal-footer">

                        <button
                            className="btn btn-secondary"
                            onClick={onClose}
                        >
                            Cancel
                        </button>

                        <button
                            className="btn btn-primary"
                            onClick={onSave}
                        >
                            Save Changes
                        </button>

                    </div>

                </div>
            </div>
        </div>
    );
}

export default FoodEditModal;