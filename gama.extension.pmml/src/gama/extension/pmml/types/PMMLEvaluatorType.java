package gama.extension.pmml.types;

import org.jpmml.evaluator.Evaluator;

import gama.annotations.type;
import gama.annotations.support.IConcept;
import gama.api.exceptions.GamaRuntimeException;
import gama.api.gaml.types.GamaType;
import gama.api.gaml.types.IType;
import gama.api.gaml.types.ITypesManager;
import gama.api.runtime.scope.IScope;
import gama.extension.pmml.skills.PredictionOperators;

@type(name = "evaluator", id = PMMLEvaluatorType.id, wraps = { PMMLEvaluator.class }, concept = { IConcept.TYPE, "pmml" })
public class PMMLEvaluatorType extends GamaType<PMMLEvaluator>{

	final static int id = IType.BEGINNING_OF_CUSTOM_TYPES + 4532236;

	public PMMLEvaluatorType(final ITypesManager tm) { super(tm); }

	@Override
	public PMMLEvaluator getDefault() {
		return null;
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	public PMMLEvaluator cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if (obj instanceof PMMLEvaluator eval) {
			return eval;
		}
		else if (obj instanceof Evaluator eval) {
			return new PMMLEvaluator(eval);
		}
		else if (obj instanceof String path) {
			return new PMMLEvaluator(PredictionOperators.loadEvaluator(scope, path));
		}
		return null;
	}
}
