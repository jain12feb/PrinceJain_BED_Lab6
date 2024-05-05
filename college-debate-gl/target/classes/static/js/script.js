function sortTable(n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.querySelector(".table");
	switching = true;
	// Set the sorting direction to ascending:
	dir = "asc";
	// Get the table rows:
	rows = table.rows;
	// Loop through all table rows except the first one (which contains the table headers):
	while (switching) {
		switching = false;
		for (i = 1; i < (rows.length - 1); i++) {
			shouldSwitch = false;
			// Get the two elements you want to compare, one from current row and one from the next:
			x = rows[i].getElementsByTagName("TD")[n];
			y = rows[i + 1].getElementsByTagName("TD")[n];
			// Check if the two rows should switch place:
			if (dir == "asc") {
				if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
					// If so, mark as a switch and break the loop:
					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
					// If so, mark as a switch and break the loop:
					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {
			// If a switch has been marked, make the switch and mark that a switch has been done:
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
			// Increase switch count to exit the loop after a certain number of iterations (to prevent infinite loops):
			switchcount++;
		} else {
			// If no switching has been done AND the direction is "asc", set the direction to "desc" and start over:
			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			}
		}
	}

	// Get the sorting icon in the clicked column header
	var sortIcon = document.querySelectorAll('.sort-icon');
	// Remove the 'asc' and 'desc' classes from all sorting icons
	sortIcon.forEach(icon => icon.classList.remove('asc', 'desc'));

	// Determine the sorting direction and add the corresponding class to the sorting icon
	if (dir == "asc") {
		sortIcon[n].classList.add('asc');
	} else {
		sortIcon[n].classList.add('desc');
	}
}


function printTable() {
	window.print();
}

// Close all modals when a new modal is opening
$('.modal').on('show.bs.modal', function(e) {
	$('.modal').modal('hide');
});