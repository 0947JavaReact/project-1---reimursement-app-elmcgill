window.onbeforeunload = () => {
	console.log('before unload');
	fetch('http://localhost:8080/project1/getSession');
}

document.getElementById("logout").addEventListener('click', logout);

document.getElementById("review").addEventListener('click', () => {
	location.href = "../html/manager-review.html";
});

let userId;

function logout(){
	fetch('http://localhost:8080/project1/logout')
	.then(() => {
		location.href = "../html/index.html";
	});
}

async function verifyLoggedIn(){
	let res = await fetch('http://localhost:8080/project1/getSession');
	let obj = await res.json();
	
	if(obj.userid < 0){
		location.href = "../html/index.html";
	}
	else{
		userId = obj.userid;
	}
}

async function approve(id) {
	
	console.log(`Approved this reimbursement ${id}`);
	
	let date = Date.now();
	
	obj = {
		userid : userId,
		reid : id,
		date: date
	}
	
	console.log(date);
	
	let res = await fetch(`http://localhost:8080/project1/approveReimbursement`,
	{
		method: 'POST',
		headers: {
      		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify(obj)
	});
	
	await getPendingReimbursements();
	
}

async function deny(id){
	
	console.log(`Deny this reimbursement ${id}`);
	
}

function populateTable(obj){
	
	let table = document.getElementById("re-table");
	
	table.innerHTML = '<tr><th>STATUS</th><th>TYPE</th><th>SUBMITTED BY</th><th>AMOUNT</th><th>SUBMITTED DATE</th><th>RESOLVED DATE</th><th>RESOLVED BY</th></tr>';
	
	obj.forEach((obj)=> {
		let index = 1;
		
		let row=table.insertRow(index++);
		row.id = obj.reId;
		
		let status = row.insertCell(0);
		status.innerHTML = obj.statusString;
		
		let type = row.insertCell(1);
		type.innerHTML = obj.typeString;
		
		let author = row.insertCell(2);
		author.innerHTML = obj.authorString;
		
		let amount = row.insertCell(3);
		amount.innerHTML = obj.reAmount;
		
		let subDate = row.insertCell(4);
		subDate.innerHTML = new Date(obj.reSubmitted).toDateString();
		
		let resDate = row.insertCell(5);
		if(obj.reResolved !== null){
			resDate.innerHTML = new Date(obj.reResolved).toDateString();
		}
		else{
			resDate.innerHTML = 'N/A';
		}
		
		let resolver = row.insertCell(6);
		if(obj.resolverString !== null){
			resolver.innerHTML = obj.resolverString;
		}
		else{
			resolver.innerHTML = 'N/A';
		}

		let descRow = table.insertRow(index++);
		let desc = descRow.insertCell(0);
		desc.setAttribute("colspan", "7");
		desc.className = "desc";
		desc.innerHTML = `<h3>Description:</h3><p>${obj.reDesc}</p>`;
		
	});
	
/*
	for(let i=0; i<obj.length; i++){
		console.log(i);
		let row = table.insertRow(i+1);
		let status = row.insertCell(0);
		status.innerHTML = obj[i].statusString;
		let type = row.insertCell(1);
		type.innerHTML = obj[i].typeString;
		let amount = row.insertCell(2);
		amount.innerHTML = obj[i].reAmount;
		let subDate = row.insertCell(3);
		subDate.innerHTML = new Date(obj[i].reSubmitted).toDateString();
		let resDate = row.insertCell(4);
		
		if(obj[i].reResolved !== null){
			resDate.innerHTML = new Date(obj[i].reResolved).toDateString();
		}
		else{
			resDate.innerHTML = 'N/A';
		}
		
		let resolver = row.insertCell(5);
		if(obj[i].resolverString !== null){
			resolver.innerHTML = obj[i].resolverString;
		}
		else{
			resolver.innerHTML = 'N/A';
		}
	}
	*/
	
}

document.getElementById("filter").addEventListener('click', async () => {
	let status = document.getElementById("status").value;
	console.log(status);
	if(status<3){
		let res = await fetch(`http://localhost:8080/project1/filterReimbursements?status=${status}`);
		let obj = await res.json();
		populateTable(obj);
	}
	else{
		let obj = await retreiveAllReimbursements();
		populateTable(obj);
	}
});

var retreiveAllReimbursements = async () => {
	let res = await fetch(`http://localhost:8080/project1/getAllReimbursements`);
	let obj = await res.json();
	return obj;
}

let init = async () => {
	await verifyLoggedIn();
	console.log(userId);
	let rows = await retreiveAllReimbursements();
	populateTable(rows);
}

init();