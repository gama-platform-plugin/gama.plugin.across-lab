package gama.extension.pmml.types;


import java.util.List;
import java.util.Map;

import org.dmg.pmml.MiningFunction;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.OutputField;
import org.jpmml.evaluator.ResidualField;
import org.jpmml.evaluator.TargetField;

import gama.annotations.doc;
import gama.annotations.getter;
import gama.annotations.variable;
import gama.annotations.vars;
import gama.api.exceptions.GamaRuntimeException;
import gama.api.gaml.types.IType;
import gama.api.gaml.types.Types;
import gama.api.runtime.scope.IScope;
import gama.api.types.list.GamaListFactory;
import gama.api.types.list.IList;
import gama.api.types.misc.IValue;
import gama.api.utils.json.IJson;
import gama.api.utils.json.IJsonValue;

@vars({
	@variable(
			name = "input_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all input fields.")
	),	
	@variable(
			name = "output_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all output fields.")
	),	
	@variable(
			name = "active_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all active fields.")
	),	
	@variable(
			name = "target_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all target fields.")
	),
	@variable(
			name = "summary",
			type = IType.STRING,
			doc = @doc("Returns a summary representing the evaluator.")
	),
	@variable(
			name = "residual_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all residual fields.")
	),
	@variable(
			name = "supplementary_fields",
			type = IType.LIST,
			doc = @doc("Returns the list of the names of all supplementary fields.")
	),	
})
public class PMMLEvaluator implements Evaluator, IValue{
	
	
	private final Evaluator internalEvaluator;
	
	public PMMLEvaluator(final Evaluator eval) {
		internalEvaluator = eval;
	}
	
	
	public static PMMLEvaluator from(final Evaluator eval) {
		return new PMMLEvaluator(eval);
	}
	
	@Override
	public IType<?> getGamlType() {
		return Types.get(PMMLEvaluatorType.id);
	}

	@Override
	public IJsonValue serializeToJson(final IJson json) {
		return json.typedObject(getGamlType());
	}

	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return super.toString();
	}

	@Override
	public IValue copy(IScope scope) throws GamaRuntimeException {
		//TODO: this is not really a copy, we just create a new pointer on the same evaluator
		return new PMMLEvaluator(internalEvaluator);
	}


	@Override
	public List<InputField> getInputFields() {
		return internalEvaluator.getInputFields();
	}


	@Override
	public List<InputField> getActiveFields() {
		return internalEvaluator.getActiveFields();
	}


	@Override
	public List<TargetField> getTargetFields() {
		return internalEvaluator.getTargetFields();
	}


	@Override
	public List<OutputField> getOutputFields() {
		return internalEvaluator.getOutputFields();
	}

	@getter("summary")
	@Override
	public String getSummary() {
		return internalEvaluator.getSummary();
	}


	@Override
	public MiningFunction getMiningFunction() {
		return internalEvaluator.getMiningFunction();
	}


	@Override
	public Evaluator verify() {
		return internalEvaluator.verify();
	}


	@Override
	public Map<String, ?> evaluate(Map<String, ?> arguments) {
		return internalEvaluator.evaluate(arguments);
	}


	@Override
	public List<ResidualField> getResidualFields() {
		return internalEvaluator.getResidualFields();
	}


	@Override
	public List<InputField> getSupplementaryFields() {
		return internalEvaluator.getSupplementaryFields();
	}
	
	@getter("input_fields")
	public IList<String> inputFields(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getInputFields().stream().map(f -> f.getName()).toList());
		return names;
	}
	
	@getter("output_fields")
	public IList<String> getOutputFieldNames(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getOutputFields().stream().map(f -> f.getName()).toList());
		return names;
	}

	@getter("active_fields")
	public IList<String> getActiveFieldNames(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getActiveFields().stream().map(f -> f.getName()).toList());
		return names;
	}
	
	@getter("target_fields")
	public IList<String> getTargetFieldNames(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getTargetFields().stream().map(f -> f.getName()).toList());
		return names;
	}
	
	@getter("residual_fields")
	public IList<String> getResidualFieldNames(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getResidualFields().stream().map(f -> f.getName()).toList());
		return names;
	}
	
	@getter("supplementary_fields")
	public IList<String> getSupplementaryFieldNames(){
		IList<String> names = GamaListFactory.create(Types.STRING);
		names.addAll(internalEvaluator.getSupplementaryFields().stream().map(f -> f.getName()).toList());
		return names;
	}

}
