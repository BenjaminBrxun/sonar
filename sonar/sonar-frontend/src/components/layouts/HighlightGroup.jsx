function HighlightButton({label, isActive, onToggle}) {
        return (
            <button
            type="button"
            className={`filterButton ${isActive ? 'active' : ''}`}
            onClick={() => onToggle(label)}
            >
                {label}
            </button>
        )
    }

    export function HighlightGroup({options, selected, setSelected}) {
        const handleToggle = (label) => {
            setSelected((prev) => (prev === label ? null : label))
        }
        return (
            <div className="highlightGroup">
                {options.map((label) => (
                    <HighlightButton
                    key={label}
                    label={label}
                    isActive={selected === label}
                    onToggle={handleToggle}
                    />
                ))}
            </div>
        )
    }

    export function MultiSelectHighlightGroup({options, selected, setSelected}) {
        const handleToggle = (label) => {
            setSelected((prev) => prev.includes(label) ? prev.filter((l) => l !== label) : [...prev, label])
        }
        return (
            <div className="highlightGroup">
                {options.map((label) => (
                    <HighlightButton
                        key={label}
                        label={label}
                        isActive={selected.includes(label)}
                        onToggle={handleToggle}
                    />
                ))}
            </div>
        )
    }