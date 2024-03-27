import { defineStore } from 'pinia';

interface ObjectList {
	[key: string]: string[];
}

export const usePermissStore = defineStore('permiss', {
	state: () => {
		const keys = localStorage.getItem('ms_keys');
		return {
			key: keys ? JSON.parse(keys) : <string[]>[],
			defaultList: <ObjectList>{
				admin: ['1', '2', '3', '4', '5'],
				user: ['1', '2', '3', '11', '13', '14', '15']
			}
		};
	},
	actions: {
		handleSet(val: string[]) {
			this.key = val;
		}
	}
});


export const permission = [
	{
		name: 'permission',
		value: {
			mounted(el: any, binding: any) {
				const type = binding.value || '';
				let status = false;
				const powers = JSON.parse(<string>localStorage.getItem('powers'));
			/*	Object.entries(powers).forEach(([k, v]) => {
					if(powers[k].path === type[0] && powers[k].enable === 1){
						status = true;
						return false;
					}
				});*/
				status = true;
				if (!status) {
					el.style.display = 'none'
				}
			}
		}
	}
]

