package vn.toancauxanh.vm;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.mesg.MZul;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.QModel;
import vn.toancauxanh.service.BasicService;

public class BaseValid extends AbstractValidator {
	private static transient final Logger log = LogManager.getLogger(BaseValid.class.getName());

	@Override
	public void validate(final ValidationContext ctx) {
		final ValidationMessages vmsgs = (ValidationMessages) ctx.getValidatorArg("vmsg");
		if (vmsgs != null) {
			vmsgs.clearKeyMessages(Throwable.class.getSimpleName());
			vmsgs.clearMessages(ctx.getBindContext().getComponent());
		}
		
		
		System.out.println("field:" + ctx.getProperty() + "" + ctx.getProperty().getValue());
		System.out.println("validate: " + (validateConstraint(ctx) && validateUnique(ctx)
				&& validatePasswords(ctx) && validateEmail(ctx)));
	}

	private boolean validateConstraint(final ValidationContext ctx) {
		boolean result;
		final Object constraint = ctx.getValidatorArg("constraint");
		if (constraint == null) {
			result = true;
		} else {
			try {
				log.info(constraint);
				new SimpleConstraint(constraint.toString()).validate(null, ctx.getProperty().getValue());
				result = true;
			} catch (final WrongValueException ex) {
				Object msg;
				Object msgid = ctx.getValidatorArg("msgid");
				if (ex.getCode() == MZul.EMPTY_NOT_ALLOWED) {
					if (msgid == null) {
						msg = "Không được để trống trường này.";
					} else {
						msg = "Vui lòng nhập!";
					}

				} else if (ex.getCode() == MZul.NO_TODAY) {
					msg = "no today";
				} else if (ex.getCode() == MZul.NO_NEGATIVE) {
					System.out.println("NO_NEGATIVE");
					msg = "Vui lòng nhập số dương";
				} else if (ex.getCode() == MZul.NO_POSITIVE) {
					System.out.println("NO_POSITIVE");
					msg = "no positive";
				} else if (ex.getCode() == MZul.NO_FUTURE) {
					System.out.println("NO_FUTURE");
					msg = "Không được lớn hơn ngày hiện tại";
				} else if (ex.getCode() == MZul.NO_ZERO) {
					msg = "Vui lòng nhập lớn hơn 0";
				} else if (ex.getCode() == MZul.NO_PAST) {
					msg = "Không được nhỏ hơn ngày hiện tại";
				} else if (ex.getCode() == MZul.NO_POSITIVE_ZERO) {
					msg = "NO_POSITIVE_ZERO";
				} else if (ex.getCode() == MZul.NO_POSITIVE_NEGATIVE_ZERO) {
					msg = "NO_POSITIVE_NEGATIVE_ZERO";
				} else if (ex.getCode() == MZul.NO_POSITIVE_NEGATIVE) {
					msg = "NO_POSITIVE_NEGATIVE";
				} else if (ex.getCode() == MZul.NO_FUTURE_TODAY) {
					msg = "NO_FUTURE_TODAY";
				} else if (ex.getCode() == MZul.NO_PAST_TODAY) {
					msg = "NO_PAST_TODAY";
				} else if (ex.getCode() == MZul.NO_FUTURE_PAST_TODAY) {
					msg = "NO_FUTURE_PAST_TODAY";
				} else if (ex.getCode() == MZul.NO_FUTURE_PAST) {
					msg = "NO_FUTURE_PAST";
				} else if (ex.getCode() == MZul.VALUE_NOT_MATCHED) {
					msg = "VALUE_NOT_MATCHED";
				} else if (ex.getCode() == 655433733) {
					msg = "Chỉ được nhập số dương";
				} else if (ctx.getValidatorArg("cmsg") == null) {
					msg = ex.getMessage();
				} else {
					msg = ctx.getValidatorArg("cmsg");
				}
				System.out.println("ex.getCode(): " + ex.getCode());
				java.util.logging.Logger.getAnonymousLogger().info(ctx.getBindContext().getComponent() + "");
				java.util.logging.Logger.getAnonymousLogger().info(ctx.getBindContext().getComponent().getId() + "");
				System.out.println("msdid: " + msgid);
				if (msgid == null) {
					addInvalidMessage(ctx, msg.toString());
				} else {
					addInvalidMessage(ctx, msgid.toString(), msg.toString());
				}
				result = false;
			}
		}
		return result;
	}

	private boolean validatePasswords(final ValidationContext ctx) {
		final Object retype = ctx.getValidatorArg("password");
		boolean result;
		if (retype == null) {
			result = true;
		} else {
			Object pass = ctx.getProperty().getValue();
			if (pass == null) {
				pass = "";
			}
			if (retype.equals(pass)) {
				result = true;
			} else {
				addInvalidMessage(ctx, "Xác nhận mật khẩu không trùng khớp!");
				result = false;
			}
		}
		return result;
	}

	private boolean validateEmail(final ValidationContext ctx) {
		String email = (String) ctx.getValidatorArg("email");
		boolean result;
		if (email == null || email.trim().matches(".+@.+\\.[a-z]+")) {
			result = true;
		} else {
			addInvalidMessage(ctx, "Địa chỉ email không đúng định dạng.");
			result = false;
		}
		return result;
	}

	private boolean validateUnique(final ValidationContext ctx) {
		boolean result;
		final Object field = ctx.getValidatorArg("field");
		if (field == null) {
			result = true;
		} else if (ctx.getProperty().getValue() != null) {
			final Model<?> object = (Model<?>) ctx.getValidatorArg("id");
			BasicService<Object> basicService = new BasicService<>();
			JPAQuery<?> q = basicService.find(object.getClass());
			Path<?> path = (Path<?>) q.getMetadata().getJoins().get(0).getTarget();
			PathBuilder<?> pb = new PathBuilder<>(object.getClass(), path.getMetadata().getName());
			// Model<?> a = alias(object.getClass(),
			// path.getMetadata().getName());
			// q =
			// q.where($(a.getTrangThai()).ne(basicService.core().TT_DA_XOA))
			// .where($(a.getId()).ne(object.getId()))
			q = q.where(pb.get(QModel.model.trangThai).ne(basicService.core().TT_DA_XOA))
					.where(pb.get(QModel.model.id).ne(object.getId()))
					.where(Expressions.path(Object.class, path, field.toString()).eq(ctx.getProperty().getValue()));
			if (q.fetchCount() > 0) {
				String replaceMsgs = (String) ctx.getValidatorArg("cmsg");
				if (replaceMsgs != null || !"".equals(replaceMsgs)) {
					addInvalidMessage(ctx, replaceMsgs);
				} else {
					addInvalidMessage(ctx, "Giá trị này đã tồn tại trong hệ thống.");
				}

				result = false;
			} else {
				result = true;
			}
		} else {
			result = true;
		}

		return result;
	}
}
