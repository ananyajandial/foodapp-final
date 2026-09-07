

function ResultView({ error, dataList, viewTitle, onEditFood , onDeleteFood  , onStatusUpdate , role,
setShowOrderModal, setSelectedFood , onCancelOrder}) {

const isSimpleMessage = 
    typeof dataList === "string" ||
    (dataList && typeof dataList === "object" &&
     Object.keys(dataList).length === 1 &&
     dataList.message);

    if (error) {
        return (
            <div className="card shadow p-4 mt-4 bg-dark text-light">
                <h3 className="mb-3 text-danger">
                    Error
                </h3>

                <p className="mb-0">
                    {error}
                </p>
            </div>
        );
    }

    if (
        !dataList || (Array.isArray(dataList) && dataList.length === 0)
    ) {
        return null;
    }

    const isFoodTable = Array.isArray(dataList) && dataList.length > 0 &&
        "foodId" in dataList[0] &&
        "foodName" in dataList[0];

    const isOrderTable = Array.isArray(dataList) &&
        dataList.length > 0 &&
        "orderId" in dataList[0];

    return (
        <div className="card shadow p-4 mt-4">

            <h3 className="mb-3">
                {viewTitle}
            </h3>

{isSimpleMessage ? ( <div className="alert alert-success">
        {typeof dataList === "string"
            ? dataList
            : dataList.message}
    </div>

) : Array.isArray(dataList) ? (

<table className="table table-bordered table-striped">

    <thead>
         <tr>
             {Object.keys(dataList[0] || {}).map(key => (
            <th key={key}>{key}</th>
                            ))}

{isFoodTable && <th>Actions</th>}

{isOrderTable && role === "ROLE_ADMIN" && (
    <th>Actions</th>
)}

{isOrderTable && role === "ROLE_USER" && (
    <td>
        {item.status?.toLowerCase() === "placed" && (
            <button
                className="btn btn-danger btn-sm"
                onClick={() => onCancelOrder(item.orderId)}
            >
                Cancel Order
            </button>
        )}
    </td>
)}


            </tr>
    </thead>

    <tbody>
           {dataList.map((item, index) => (

            <tr key={index}>
                {Object.values(item).map((value, i) => (
                <td key={i}>
                    {String(value)}
                </td>
            ))}
{isFoodTable && (
<td>

{role === "ROLE_ADMIN" && (
<>
    <button
        className="btn btn-warning btn-sm me-2"
        onClick={() => onEditFood(item)}
    >
        Edit
    </button>

    <button
        className="btn btn-danger btn-sm"
        onClick={() => onDeleteFood(item.foodId)}
    >
        Delete
    </button>
</>
)}

{role === "ROLE_USER" && (
    <button
        className="btn btn-success btn-sm"
        onClick={() => {
            console.log("BUTTON CLICKED")
            console.log(item);
            setSelectedFood(item);
            setShowOrderModal(true);
        }}
    >
        Place Order
    </button>
)}

</td>
)}

{isOrderTable && role === "ROLE_ADMIN" &&( 
    <td>
         <select className="form-select form-select-sm" defaultValue={item.status}
            onChange={(e) => onStatusUpdate(item.orderId , e.target.value)}>

            <option value="placed"> placed </option>
            <option value="preparing"> preparing </option>
            <option value="delivered"> delivered </option>
            <option value="cancelled"> cancelled </option>
        </select>
    </td>
        )}
 {isOrderTable && role === "ROLE_USER" && (
    <td>
        {item.status?.toLowerCase() === "placed" && (
            <button
                className="btn btn-danger btn-sm"
                onClick={() => onCancelOrder(item.orderId)}
            >
                Cancel Order
            </button>
        )}
    </td>
)}
    </tr>
        ))}

</tbody>
    </table> ) : (
        <table className="table table-bordered">
            <tbody>

            {Object.entries(dataList).map(([key, value]) => (

            <tr key={key}>
                <th>{key}</th>
                    <td>{String(value)}</td>
                </tr>
         ))}
            </tbody>
        </table>

        )}
    

    </div>
    );
}

export default ResultView;