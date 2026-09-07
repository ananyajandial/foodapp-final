import { useState } from "react";

function FoodSearchCard({
    apiRequest,
    showValidationError,
    isAdmin
}) {

    const defaultSearch = {
        id: "",
        category: "",
        minPrice: "",
        maxPrice: ""
    };

    const [search, setSearch] = useState(defaultSearch);

    const handleSearch = async () => {

        if (isAdmin && search.id?.trim()) {

            await apiRequest(
                "GET",
                `/admin/food/${search.id}`
            );

        } else if (
            search.minPrice &&
            search.maxPrice
        ) {

            if (
                Number(search.minPrice) >
                Number(search.maxPrice)
            ) {
                showValidationError(
                    "Minimum price cannot be greater than maximum price"
                );
                return;
            }

            await apiRequest(
                "GET",
                `/admin/between/${search.minPrice}/${search.maxPrice}`
            );

        } else if (
            search.category &&
            search.maxPrice
        ) {

            await apiRequest(
                "GET",
                `/admin/filter?c=${search.category}&p=${search.maxPrice}`
            );

        } else if (search.category) {

            await apiRequest(
                "GET",
                `/admin/category/${search.category}`
            );

        } else if (search.maxPrice) {

            await apiRequest(
                "GET",
                `/admin/belowprice/${search.maxPrice}`
            );

        } else {

            showValidationError(
                "Please enter at least one search criteria"
            );
            return;
        }

        setSearch(defaultSearch);
    };

    return (
        <div className="card shadow p-4 h-100">

            <h3 className="text-primary mb-3">
                Food Search
            </h3>

            {isAdmin && (
                <input
                    className="form-control mb-2"
                    placeholder="Food ID"
                    value={search.id}
                    onChange={(e) =>
                        setSearch({
                            ...search,
                            id: e.target.value
                        })
                    }
                />
            )}

            <select
                className="form-control mb-2"
                value={search.category}
                onChange={(e) =>
                    setSearch({
                        ...search,
                        category: e.target.value
                    })
                }
            >
                <option value="">Select Category</option>
                <option value="FASTFOOD">FASTFOOD</option>
                <option value="CHINESE">CHINESE</option>
                <option value="SOUTHINDIAN">SOUTHINDIAN</option>
                <option value="NORTHINDIAN">NORTHINDIAN</option>
                <option value="DESSERT">DESSERT</option>
            </select>

            <input
                className="form-control mb-2"
                placeholder="Min Price"
                type="number"
                value={search.minPrice}
                onChange={(e) =>
                    setSearch({
                        ...search,
                        minPrice: e.target.value
                    })
                }
            />

            <input
                className="form-control mb-3"
                placeholder="Max Price"
                type="number"
                value={search.maxPrice}
                onChange={(e) =>
                    setSearch({
                        ...search,
                        maxPrice: e.target.value
                    })
                }
            />

            <button
                className="btn btn-primary w-100"
                onClick={handleSearch}
            >
                Search Food
            </button>

        </div>
    );
}

export default FoodSearchCard;